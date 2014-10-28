<html>
<head>
	<title>Lecturer Login page</title>
</head>
<body style="background-color:orange">
ShortURL for this lecture : <b>${lectureName}</b><br>

<script type="text/javascript" src="http://api.qrtag.net/js"></script>
<script type="text/javascript">
    var qrtag = new QRtag();
    qrtag.data(Math.random());
    qrtag.id("QRcode");
    qrtag.class_name("QRcode");
    qrtag.size(20); 
    qrtag.border(2);
    qrtag.color("#660000");
    qrtag.bgcolor("#ffffff");
    qrtag.target();
    qrtag.print_image();
</script>

<form action="./lecturerController" method="POST">
	<input type="hidden" name="lectureId" value="${lectureId}">
	<input type="submit" value="next">
</form>

</body>
</html>