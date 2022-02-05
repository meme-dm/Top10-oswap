public void changeAccountPassword(HttpServletRequest request, HttpServletResponse response) throws ServletException {
  HttpSession session = request.getSession();
​
  if (session != null) {
    User user = session.getAttribute("user");
​
    user.setPassword(request.getParameter("newPassword"));
    user.setOldPassword(request.getParameter("oldPassword"));
    user.setAccountId(request.getParameter("account"));
​
    updatePasswordFor(user);
​
    response.setContentType("application/json");
    PrintWriter pw = response.getWriter();
    pw.println(buildJSONResponse(user));
    pw.flush();
  }
}