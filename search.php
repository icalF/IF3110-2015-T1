<?php
include 'header.php';
include 'database.php';

$conn = db_init();

$q = $_GET['q'];
$query = "SELECT * FROM question WHERE topik LIKE '%" .$q. "%' OR isi LIKE '%" .$q. "%'";
$db_result = mysqli_query($conn, $query);
if (!$db_result) 
{
  $num = 0;
}
else 
{
  $num = mysqli_num_rows($db_result);
}

echo '<hr>';
if ($num == 0)
{
  echo "<h3 align='center'>Question not found</h3>";
}
else
{
  $list = array();
  while ($row = mysqli_fetch_assoc($db_result)) $list[] = $row;

  foreach ($list as $row) 
  {
    $id = $row['Id'];
    $topik = $row['topik'];
    $name = $row['username'];
    $datetime = $row['datetime'];
    $mail = $row['email'];
    $vote = $row['vote'];
    $isi = $row['isi'];
    $query = "SELECT COUNT(*) FROM answer WHERE qid = $id";
    $ans = mysqli_fetch_array(mysqli_query($conn, $query))[0];
    echo 
      "<a href='view.php?id=$id'>
      <div class='question-summary'>
        <div class='votes-counter'>
          <div class='votes-counter-num'>$vote</div>
          <div>Votes</div>
        </div>
        <div class='answers-counter'>
          <div class='answers-counter-num'>$ans</div>
          <div>Answers</div>
        </div>
        <div class='question-text'>
          <div class='mini-title'><b>$topik</b></div>
          <div>$isi</div>
          <div class='question-time-menu'>
            <div class='question-menu'>
              <a href='edit.php?id=$id'>Edit</a>  
              <a href='delete.php?id=$id' onclick='return deleteConfirm()'>Delete</a>              
            </div>
            <div class='author-info'>
              oleh <a href='mailto:$mail'>$name</a> pada $datetime
            </div>
          </div>
        </div>
      </div></a>";
  }
}
mysqli_close($conn);
?>

</body>
</html>