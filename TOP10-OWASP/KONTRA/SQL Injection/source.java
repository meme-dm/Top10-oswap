private void unsubscribeUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
  JSONObject json = new JSONObject();
  String email = request.getParameter("email");
// Lấy dữ liệu từ tham số email
  try {
    Connection db = DBHelper.getConnection();
    Statement stmt = db.createStatement();

    String sql = "SELECT email FROM user WHERE email = '" + email + "'";
    // SELECT email FROM user WHERE email = ''
    // Truyền tham số email vào câu truy vấn sql tuy nhiên ko có bộ lọc.
    ResultSet results = stmt.executeQuery(sql);

    if (results.next()) {
      email = DbHelper.GetEmailColumn(results);
      DbHelper.UnsubscribeUser(email);
    }

    json.put("email", email);
    json.put("success", true);
    // Trả về kết quả câu truy vấn ra màn hình.
  } catch (SQLException ex) {
    json.put("error", ex.getMessage());
    json.put("success", false);
  }

  response.setContentType("application/json");
  response.getWriter().println(json.toString());
}