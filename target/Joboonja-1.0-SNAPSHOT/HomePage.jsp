<%--
  Created by IntelliJ IDEA.
  User: Mohsen
  Date: 2019-03-20
  Time: 00:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <script>
        function GET() {
            var xhttp = new XMLHttpRequest();
            xhttp.onreadystatechange = function() {
                if (this.readyState == 4 && this.status == 200) {
                    document.getElementById("GET").innerHTML = "<h4 style='margin-bottom: 0px'>" + this.status + "</h4>" +
                        "<h4 style='margin-top: 0px'>" + this.responseText + "</h4>";
                }
                else if (this.readyState == 4) {
                    document.getElementById("GET").innerHTML = "<h4>" + this.status + "</h4>";
                }
            };
            xhttp.open("GET", "/project?applicantUser=4", true);
            xhttp.send();
        }

        function POST() {
            var xhttp = new XMLHttpRequest();
            xhttp.onreadystatechange = function() {
                if (this.readyState == 4) {
                    document.getElementById("POST").innerHTML = "<h4>" + this.status + "</h4>";
                }
            };
            var params = new FormData();
            params.append('action', 'addSkill');
            params.append('skill', 'C++');
            xhttp.open("POST", "/user/1", true);
            xhttp.send(params);
        }

    </script>

</head>
<body>
    <h2>This is javascript test!!!</h2>

    <div id="GET">
        <button type="button" onclick="GET()">GET</button>
    </div>
    <div id="POST">
        <button type="button" onclick="POST()">POST</button>
    </div>
</body>
</html>
