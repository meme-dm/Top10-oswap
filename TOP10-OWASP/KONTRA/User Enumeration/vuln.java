private void resetPassword(HttpServletRequest request, HttpServletResponse response) {
  String email = request.getParameter("email");
  String message = "";
  User user = DBHelper.findUserByEmail(email);

  if (user == null) {
    message = "Could not reset password because there is no registered user with that e-mail address";
  } else {
    String newPassword = PasswordUtil.generateRandomPassword();
    StringBuilder hostBuilder = new StringBuilder()
      .append(request.getScheme())
      .append("://")
      .append(InetAddress.getLoopbackAddress().getHostName())
      .append(request.getContextPath())
      .append("/resetpwd?pwd=")
      .append(newPassword);

    DBHelper.updateUserDetails(email, newPassword);
    MailHelper.sendMail("admin", email, hostBuilder.toString());

    message = "Please check your email inbox to continue";
  }

  forwardUserToLogin(request, "login", message);
}