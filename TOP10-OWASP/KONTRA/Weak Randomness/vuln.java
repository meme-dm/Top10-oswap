public void generateToken(HttpServletRequest request) {
  HttpSession session = request.getSession();
  String email = session.getAttribute("email");
  String token = generateTokenBy(email);

  DatabaseManager.saveToken(email, token);
  MailSenderManager.sendEmail(email, token);
}

private String generateTokenBy(String email) {
  StringBuilder stringBuilder = new StringBuilder();

  stringBuilder
    .append(LocalTime.now().getHour())
    .append(LocalTime.now().getMinute())
    .append(LocalTime.now().getSecond())
    .append(email);

  return Base64.getEncoder().encodeToString(stringBuilder.toString().getBytes());
}