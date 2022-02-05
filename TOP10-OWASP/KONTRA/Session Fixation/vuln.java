private boolean authenticateUser(HttpServletRequest request) {
  String username = request.getParameter("username");
  String password = request.getParameter("password");
  HttpSession session = request.getSession(false);
// Ứng dụng lấy username , password và sesion ID từ requests  
//if (session != null) {
//    session.invalidate();
//  }

  try {
    authenticateUserWith(username, password, session);
    // Sau đó nó kiểm tra username và password ,miễm là phiên còn hạn và hợp lệ thì nó sẽ lấy luôn phiên đó mà không tạo mới.
    request.getSession(true);
    return true;
  } catch (Exception e) {
    LOGGER.error("Error occurred while authenticating: ", e);
    return false;
  }
}