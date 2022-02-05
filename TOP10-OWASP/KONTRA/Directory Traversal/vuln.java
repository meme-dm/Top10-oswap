private static final String THUMBNAIL_BASE_PATH = "/blob/sku/items/images";

private void getThumbnailForProduct(HttpServletRequest request, HttpServletResponse response) throws IOException {
  String folderName = request.getParameter("folderName");
  String fileName = request.getParameter("fileName");
  // trích xuất các tham số folderName và fileName từ GET
  String path = THUMBNAIL_BASE_PATH + folderName + fileName;
  //  đường dẫn được xây dựng từ hai tham số trên và sau đó nó sẽ được đọc nếu tồn tại . 
  File file = new File(path);
  // FIX vuln
  // String canonicalPath = file.getCanonicalPath();
  // if (canonicalPath.startsWith(THUMBNAIL_BASE_PATH)) {
  //   buildResponse(response, file);
  // } else {
  //   throw new GenericException("Unauthorized access");
  // }
  buildResponse(response, file);
  // Cuối cùng trả về kết quả trên trình duyệt
}

private void buildResponse(HttpServletResponse response, File file) throws IOException {
  response.setContentType("image/png");
  OutputStream os = response.getOutputStream();
  BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
  byte[] buffer = new byte[1024];
  int read;

  while ((read = bis.read(buffer)) != -1) {
    os.write(buffer, 0, read);
  }
  
  bis.close();
  os.flush();
  os.close();
}