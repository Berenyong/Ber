<%@ include file="db.jsp" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
</head>

<body>
    <jsp:include page="header.jsp"></jsp:include>

    <section style="position: fixed; top: 70px; left: 0px; width: 100%; height: 100%; background-color: lightgray;">
        <h2 style="text-align: center;"> 회원정보 조회 </h2>
        <form style="display: flex; align-items: center; justify-content: center; text-align: center">
            <table border="1">
                <tr>
                    <td style="width:150px">수강월</td>
                    <td style="width:150px">회원번호</td>
                    <td style="width:150px">회원명</td>
                    <td style="width:150px">강의명</td>
                    <td style="width:150px">강의장소</td>
                    <td style="width:150px">수강료</td>
                    <td style="width:150px">등급</td>
                </tr>
                <%
                request.setCharacterEncoding("UTF-8");

                try {
                    String sql = "select regist_month, c_no, c_name, class_name, class_area, tuition, grade" +
                    + "from tbl_teacher_202201 te, tbl_member_202201 me, tbl_class_202201 cl"
                    + "where te.teacher_code=cl.teacher_code and me.c_no=cl.c_no";
                } catch(Exception e) {
                    e.printStackTrace();
                }

                %>
            </table>
        </form>
    </section>

    <jsp:include page="footer.jsp"></jsp:include>
</body>

</html>