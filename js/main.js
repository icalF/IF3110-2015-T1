function validateEmail(mail) {
  var reg = /^(([^<>()[\]\.,;:\s@\"]+(\.[^<>()[\]\.,;:\s@\"]+)*)|(\".+\"))@(([^<>()[\]\.,;:\s@\"]+\.)+[^<>()[\]\.,;:\s@\"]{2,})$/i;
  if (reg.test(mail)) {
    return true;
  } else {
    alert('Wrong email format');
    return false;
  }
};

function validateForm() {
  if (null === document.getElementById('topic')) {
    var topic = 'dummy';
  } else {
    var topic = document.myform.topic.value;
  }

  var name = document.myform.name.value;
  var email = document.myform.email.value;
  var content = document.myform.content.value;
  if (name == '') {
    alert('Username cannot be empty')
    return false;
  }
  else if (email == '') {
    alert('Email cannot be empty')
    return false;
  }
  else if (topic == '') {
    alert('Topic cannot be empty')
    return false;
  }
  else if (content == '') {
    alert('Content cannot be empty')
    return false;
  }
  else return validateEmail(email);
};

function voter(vote_change, answer, post_id, id) {
  var vote = parseInt(document.getElementById(id).innerHTML) + vote_change;
  var xmlhttp = new XMLHttpRequest();       // assumming use IE 7+ version or another browser

  xmlhttp.onreadystatechange = function() {
    if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
      document.getElementById(id).innerHTML = vote;
    }
  }

  var db_name = answer ? 'answer' : 'question';
  var request = "id="+post_id+"&dbname="+db_name+"&val="+vote;

  xmlhttp.open("POST", "vote", true);
  xmlhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
  xmlhttp.send(request);
};

function deleteConfirm () {
  return confirm('Are you sure to delete this question?');
}