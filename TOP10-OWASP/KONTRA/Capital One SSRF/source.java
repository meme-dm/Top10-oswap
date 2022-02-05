/**
 * Downloads stored image using storage service. Check if url parameter is present if not return preview page
 * Adds image as Base64 model attribute.
 * In case of error adds errorMessage and errorStacktrace messages.
 * XXX Part of cloud migration project. See ticket CO-WEB-INFRA-21103 for details - team needs time to figure our the AWS S3 API so go easy on us ;)
 * @param httpRequest stored file url
 * @param model       model
 * @return preview page
 */
@GetMapping("/preview")
public String documentPreview(HttpServletRequest httpRequest, Model model) {
  // documentPreview : hiển thị hình ảnh tải lên
  String queryStringParams = httpRequest.getQueryString();
  String queryString = StringUtils.substringAfter(queryStringParams, "url=");
// Phương thức này trích xuất URL từ S3 được truyền qua tham số url và gán nó cho biến queryString.

  if (StringUtils.isBlank(queryString)) {
    log.error("Missed 'url' query param");
    return "preview";
  }

  try {
    DownloadFileResponse downloadFileResponse = storageService.load(queryString);
    //Sau đó queryString sẽ đc đưa vào storageService.load() để tải hình ảnh xuống 
    model.addAttribute("image", new String(Base64.getEncoder().encode(IOUtils.toByteArray(downloadFileResponse.getContent()))));
  } catch (Exception e) {
    log.error("Failed to download file " + queryString, e);
    model.addAttribute("errorMessage", e.getMessage());
    model.addAttribute("errorStackTrace", ExceptionUtils.getStackTrace(e));
  }

  return "preview";
}

/**
 * Uploads input file to AWS using storage service.
 * After redirects to preview page with file url.
 *
 * @param file file to be uploaded
 * @return redirect to a preview image page
 */
@PostMapping("/upload")
public String documentUpload(@RequestParam("file") MultipartFile file) {
  UploadFileResponse uploadFileResponse = storageService.store(file);
  
  return "redirect:/documents/preview?url=" + uploadFileResponse.getFileUrl();
}

/**
 * Stores file to AWS storage. Generates preSigned file Url for response.
 * In case of exception returns an empty response object.
 *
 * @param file input file
 * @return upload file response with preSigned file url
 */
@Override
public UploadFileResponse store(MultipartFile file) {
  try (ByteArrayInputStream inputStream = new ByteArrayInputStream(file.getBytes())) {
    String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
    PutObjectResult putObjectResult = amazonS3.putObject(new PutObjectRequest(bucketName, fileName, inputStream, null));
    log.info("File {} successfully uploaded {}", fileName, putObjectResult.toString());

    return UploadFileResponse.builder().fileUrl(preSignedImageUrl(bucketName, fileName)).build();
  } catch (AmazonServiceException ase) {
   log.error("Caught an AmazonServiceException, which means your request made it to Amazon S3, but was rejected with an error response for some reason.", ase);
  } catch (AmazonClientException ace) {
    log.error("Caught an AmazonClientException, which means the client encountered an internal error while trying to communicate with S3, such as not being able to access the network.", ace);
  } catch (IOException e) {
    log.error("Can't upload file to S3", e);
  }

  return UploadFileResponse.builder().build();
}

/**
 * Downloads file from specified url.
 *
 * @param url file url
 * @return response with file name and file content
 * @throws IOException if can't download file
 */
@Override
public DownloadFileResponse load(String url) throws IOException {
  CloseableHttpClient client = HttpClients.createDefault();
  HttpGet httpGet = new HttpGet(url);
//phương thức load() gọi hàm HttpGet () của Java để truy xuất tệp hình ảnh. 
//Rất tiếc, không có kiểm tra xác thực đầu vào nào được thực hiện trên url tham số yêu cầu, 
//cho phép Bob kiểm soát việc tải URL tùy ý thông qua phương thức HttpGet ().
  InputStream is  = client.execute(httpGet).getEntity().getContent(); // send request

  return DownloadFileResponse.builder().fileName(UUID.randomUUID().toString()).content(is).build();
}
