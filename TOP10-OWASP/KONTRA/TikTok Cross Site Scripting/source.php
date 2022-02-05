<div class="container-fluid" style="padding-left: 100px; padding-right: 100px;">
            <div class="row">
                <div class="col-md-12">
                    <c:if test="${find}">
                    <!-- Hiện thị kết quả tìm kiếm và kiểm tra xem có tìm kiếm thành công ko -->
                        <div class="search-result">
                            <h2>Search results for <strong>${terms}</strong></h2>
                            <ul class="listeProject">
                            <c:forEach items="${searchResult}" var="p">
                                <a class="article-item d-flex" href="/project/${p.getId()}">
                                    <li class="article-item-content">
                                        <h3 class="article-item-title">${p.getTitle()}</h3>
                                        <p>${p.getDescription()}</p>
                                    </li>
                                </a>
                            </c:forEach>
                            </ul>
                        </div>
                    </c:if>
                    <c:if test="${!find}"> //If a matching article was not found
                    <!-- Neeusko tìm thấy thì sẽ trả về kq bằng câu lệnh phía dưới -->
                        <h2>No results for <span><%= request.getparmeter("q") %></span></h2>
                        <!-- request.getparmeter lấy từ khóa và hiển thị trực tiếp trên trang web ,
                        tuy nhiên nó lại ko có bất kì xác thực hay bộ lọc nào dấn đến có thể chèn mã độc. -->
                        <ul>
                            <li> Browse the articles listed in our help topics.</li>
                            <li> Make sure all words are spelled correctly. </li>
                            <li> Try different keywords.</li>
                        </ul>
                    </c:if>
                </div>
            </div>
        </div>