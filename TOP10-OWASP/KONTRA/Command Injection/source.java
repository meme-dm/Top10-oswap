private void handleTwoFactorAuthentication(final HttpServletRequest request, final HttpServletResponse response) throws IOException {
  String phoneNumber = request.getParameter("phone");

  if (phoneNumber == null) {
    response.getWriter().append("phone attribute is missing within the request");
    return;
  }

  Runtime runtime = Runtime.getRuntime();

  try {
    Process process = runtime.exec(new String[] {
      "bash",
      "-c",
      "curl -o -I -L -s -w %{http_code}" + API_URL + "?number=" + phoneNumber
      // curl -o -l -L -s -w %{http_code} https://apigree.sms.gateway.com?number=2-666-777-8888
    });
    // private static final String PHONE_REGEX = "^(\\+\\d{1,2}\\s)?\\(?\\d{3}\\)?[\\s.-]\\d{3}[\\s.-]\\d{4}$";

    String processResponse = readResponseFor(process);
    response.getWriter().append(processResponse);
  } catch (InterruptedException | IOException e) {
    LOGGER.error("Error while processing the request: ", e);
    response.getWriter().append(e.getMessage());
  }
}