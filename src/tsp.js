SCALE = 2;
MILES_PER_PIXEL = 0.2404444;
name = "default";
x = [
  /*Middletown*/ 896, /*Erie*/ 154, /*Edinboro*/ 143, /*Meadville*/ 135,
  /*Hermitage*/ 65, /*Grove City*/ 146, /*New Castle*/ 85,
  /*Cranberry Twp*/ 139, /*Pittsburgh*/ 160, /*Monroeville*/ 208,
  /*West Mifflin*/ 190, /*Greensburg*/ 264, /*Seven Springs*/ 319,
  /*Somerset*/ 368, /*Johnstown*/ 405, /*Indiana*/ 353, /*Punxsutawney*/ 396,
  /*Brookville*/ 372, /*Clarion*/ 303, /*Warren*/ 362, /*Bradford*/ 473,
  /*St Marys*/ 492, /*DuBois*/ 445, /*Clearfield*/ 517, /*Coudersport*/ 613,
  /*State College*/ 649, /*Altoona*/ 525, /*Hollidaysburg*/ 527,
  /*Huntington*/ 614, /*Wellsboro*/ 774, /*Mansfield*/ 825, /*Sayre*/ 949,
  /*Lock Haven*/ 743, /*Montoursville*/ 863, /*Williamsport*/ 844,
  /*Lewisburg*/ 871, /*Bloomsburg*/ 967, /*Harrisburg*/ 872, /*Carlisle*/ 798,
  /*Chambersburg*/ 694, /*Hanover*/ 851, /*York*/ 908, /*Hershey*/ 925,
  /*Lancaster*/ 1005, /*Lebanon*/ 980, /*Reading*/ 1091, /*Wilkes-Barre*/ 1096,
  /*Scranton*/ 1143, /*Allentown*/ 1193, /*Easton*/ 1249,
  /*King of Prussia*/ 1217, /*Philadelphia*/ 1268,
];

y = [
  /*Middletown*/ 622 /*Erie*/, 40 /*Edinboro*/, 117 /*Meadville*/, 187,
  /*Hermitage*/ 307 /*Grove City*/, 330 /*New Castle*/, 377 /*Cranberry Twp*/,
  474, /*Pittsburgh*/ 548 /*Monroeville*/, 555 /*West Mifflin*/,
  573 /*Greensburg*/, 591, /*Seven Springs*/ 678 /*Somerset*/,
  683 /*Johnstown*/, 587 /*Indiana*/, 498, /*Punxsutawney*/ 402 /*Brookville*/,
  336 /*Clarion*/, 320 /*Warren*/, 132, /*Bradford*/ 98 /*St Marys*/,
  259 /*DuBois*/, 349 /*Clearfield*/, 378,
  /*Coudersport*/ 155 /*State College*/, 448 /*Altoona*/, 530 /*Hollidaysburg*/,
  558, /*Huntington*/ 543 /*Wellsboro*/, 161 /*Mansfield*/, 144 /*Sayre*/, 90,
  /*Lock Haven*/ 347 /*Montoursville*/, 311 /*Williamsport*/, 313 /*Lewisburg*/,
  397, /*Bloomsburg*/ 384 /*Harrisburg*/, 603 /*Carlisle*/,
  628 /*Chambersburg*/, 707, /*Hanover*/ 747 /*York*/, 697 /*Hershey*/,
  600 /*Lancaster*/, 673, /*Lebanon*/ 583 /*Reading*/, 582 /*Wilkes-Barre*/,
  310 /*Scranton*/, 260, /*Allentown*/ 500 /*Easton*/, 474 /*King of Prussia*/,
  651 /*Philadelphia*/, 694,
];

var currentTour = [
  0, 10, 20, 30, 40, 50, 1, 11, 21, 31, 41, 51, 2, 12, 22, 32, 42, 3, 13, 23,
  33, 43, 4, 14, 24, 34, 44, 5, 15, 25, 35, 45, 6, 16, 26, 36, 46, 7, 17, 27,
  37, 47, 8, 18, 28, 38, 48, 9, 19, 29, 39, 49,
];
var bestSolution;
var bestDistance = 99999999999999.9;

function drawTour(idsInOrder) {
  var c = document.getElementById("mapCanvas");
  var ctx = c.getContext("2d");
  var img = new Image();
  w = window.innerWidth;
  h = Math.floor(w * 0.579);
  ctx.canvas.width = w;
  ctx.canvas.height = h;
  SCALE = 1370 / w;
  img.onload = function () {
    var dist = 0.0;
    ctx.drawImage(img, 0, 0, w, h);
    ctx.beginPath();
    ctx.moveTo(x[idsInOrder[0]] / SCALE, y[idsInOrder[0]] / SCALE);
    var lastX = x[idsInOrder[0]];
    var lastY = y[idsInOrder[0]];
    for (var i = 1; i < idsInOrder.length; i++) {
      var ndx = parseInt(idsInOrder[i], 10);
      ctx.lineTo(x[ndx] / SCALE, y[ndx] / SCALE);
      ctx.stroke();
      dist =
        dist +
        Math.sqrt(Math.pow(x[ndx] - lastX, 2) + Math.pow(y[ndx] - lastY, 2));
      lastX = x[ndx];
      lastY = y[ndx];
    }
    ctx.lineTo(x[idsInOrder[0]] / SCALE, y[idsInOrder[0]] / SCALE);
    ctx.stroke();
    dist =
      dist +
      Math.sqrt(
        Math.pow(x[idsInOrder[0]] - lastX, 2) +
          Math.pow(y[idsInOrder[0]] - lastY, 2)
      );
    dist = dist * MILES_PER_PIXEL;
    console.log(JSON.stringify({ name: name, dist: dist, list: idsInOrder }));

    /*$.ajax(	{
		url:"http://hbgonlinejudge.hbg.psu.edu:8080/update",
		type:"POST",
		contentType:"application/json; charset=utf-8",
		data: JSON.stringify({"name":name,"dist":dist,"list":idsInOrder})
	}).done(function() {
		console.log("Update sent");
	})*/
  };
  img.src = "images/map.png";
  var dist = 0.0;
  var lastX = x[idsInOrder[0]];
  var lastY = y[idsInOrder[0]];
  for (var i = 1; i < idsInOrder.length; i++) {
    var ndx = parseInt(idsInOrder[i], 10);
    dist =
      dist +
      Math.sqrt(Math.pow(x[ndx] - lastX, 2) + Math.pow(y[ndx] - lastY, 2));
    lastX = x[ndx];
    lastY = y[ndx];
  }
  dist =
    dist +
    Math.sqrt(
      Math.pow(x[idsInOrder[0]] - lastX, 2) +
        Math.pow(y[idsInOrder[0]] - lastY, 2)
    );
  dist = dist * MILES_PER_PIXEL;

  if (dist < bestDistance) {
    bestDistance = dist;
    $("#best_solution").html(dist.toFixed(2) + " mi");
    bestSolution = idsInOrder.slice(0);
  }
  $("#current_solution").html(dist.toFixed(2) + " mi");
  return dist;
}

function updateOptions() {
  $.ajax({
    url: "options.json?_=" + new Date().getTime(),
    type: "GET",
    success: function (data, text) {
      try {
        if (data.next1 == "1") {
          $("#next1").css("display", "inline");
        } else {
          $("#next1").css("display", "none");
        }
      } catch (err) {}
      try {
        if (data.next106 == "1") {
          $("#next106").css("display", "inline");
        } else {
          $("#next106").css("display", "none");
        }
      } catch (err) {}
      try {
        if (data.swap2 == "1") {
          $("#swap2").css("display", "inline");
        } else {
          $("#swap2").css("display", "none");
        }
      } catch (err) {}
      try {
        if (data.shift1 == "1") {
          $("#shift1").css("display", "inline");
        } else {
          $("#shift1").css("display", "none");
        }
      } catch (err) {}
      try {
        if (data.shiftm == "1") {
          $("#shiftm").css("display", "inline");
        } else {
          $("#shiftm").css("display", "none");
        }
      } catch (err) {}
      try {
        if (data.reverse == "1") {
          $("#reverse").css("display", "inline");
        } else {
          $("#reverse").css("display", "none");
        }
      } catch (err) {}
    },
    error: function (request, status, error) {
      console.log(request.responseText);
      console.log(status);
    },
  });
}

$(function () {
  var dist = drawTour(currentTour);
  setInterval(updateOptions, 1000);
});

function solutionHistory(select) {
  drawTour(solutions[select.value]);
  update_list(solutions[select.value]);
}

function next(SIZE) {
  var idsInOrder = currentTour.slice(0);
  var bestScore = 9999999999.99;
  for (var i = 0; i < SIZE; i++) {
    for (var j = 0; j < 52; j++) {
      var ndx = Math.floor(Math.random() * 52);
      var tmp = idsInOrder[j];
      idsInOrder[j] = idsInOrder[ndx];
      idsInOrder[ndx] = tmp;
    }
    var dist = getDist(idsInOrder);
    if (dist < bestScore) {
      bestScore = dist;
      bestIdsInOrder = idsInOrder.slice(0);
    }
  }
  currentTour = bestIdsInOrder;
  var dist = drawTour(currentTour);
}

function shift_one() {
  var idsInOrder = currentTour.slice(0);
  var bestIdsInOrder = idsInOrder.slice(0);
  var bestScore = getDist(idsInOrder);
  var origDist = bestScore;

  // exhaustively find the best solution of shifting one id
  // 
  for (var i = 0; i < 52; i++) {    // source id
    for (var j = 0; j < 52; j++) {  // destination id
      var tmpIds = [];
      // tmpids = idsInOrder[0..j] + idsInOrder[i] + idsInOrder[j..]
      for (var k = 0; k < j; k++) {
        if (k != i) tmpIds.push(idsInOrder[k]);
      }
      tmpIds.push(idsInOrder[i]);
      for (var k = j; k < 52; k++) {
        if (k != i) tmpIds.push(idsInOrder[k]);
      }
      var dist = getDist(tmpIds);
      if (dist < bestScore) {
        console.log("best score" + dist);
        bestScore = dist;
        bestIdsInOrder = tmpIds.slice(0);
      }
    }
  }
  if (origDist == bestScore) {
    alert("Could not improve solution");
  } else {
    currentTour = bestIdsInOrder;
    var dist = drawTour(bestIdsInOrder);
  }
}

function swap_two() {
  var idsInOrder = currentTour.slice(0);
  var bestIdsInOrder = idsInOrder.slice(0);
  var bestScore = getDist(idsInOrder);
  var origDist = bestScore;

  for (var i = 0; i < 52; i++) {
    for (var j = i + 1; j < 52; j++) {
      var tmp = idsInOrder[j];
      idsInOrder[j] = idsInOrder[i];
      idsInOrder[i] = tmp;
      var dist = getDist(idsInOrder);
      if (dist < bestScore) {
        bestScore = dist;
        bestIdsInOrder = idsInOrder.slice(0);
      }
      var tmp = idsInOrder[j];
      idsInOrder[j] = idsInOrder[i];
      idsInOrder[i] = tmp;
    }
  }
  if (origDist <= bestScore) {
    alert("Could not improve solution");
  } else {
    currentTour = bestIdsInOrder;
    var dist = drawTour(bestIdsInOrder);
  }
}

function reverse() {
  var idsInOrder = currentTour.slice(0);
  var bestIdsInOrder = idsInOrder.slice(0);
  var bestScore = getDist(idsInOrder);
  var origDist = bestScore;

  for (var i = 0; i < 52; i++) {
    for (var j = 0; j < 52; j++) {
      var tmpIds = [];
      for (k = j; k != i; k = (k + 51) % 52) {
        tmpIds.push(idsInOrder[k]);
      }
      tmpIds.push(idsInOrder[i]);
      for (k = (j + 1) % 52; k != i; k = (k + 1) % 52) {
        tmpIds.push(idsInOrder[k]);
      }
      var dist = getDist(tmpIds);
      if (dist < bestScore) {
        console.log("best score" + dist);
        bestScore = dist;
        bestIdsInOrder = tmpIds.slice(0);
      }
    }
  }
  if (origDist <= bestScore) {
    alert("Could not improve solution");
  } else {
    currentTour = bestIdsInOrder;
    var dist = drawTour(bestIdsInOrder);
  }
}

function shift_section() {
  var idsInOrder = currentTour.slice(0);
  var bestIdsInOrder = idsInOrder.slice(0);
  var bestScore = getDist(idsInOrder);
  var origDist = bestScore;

  var done = false;
  for (var i = 0; !done && i < 52; i++) {
    for (var j = 0; !done && j < 52; j++) {
      for (var l = (j + 1) % 52; !done && l != i; l = (l + 1) % 52) {
        var tmpIds = [];
        for (k = i; k != j; k = (k + 1) % 52) {
          tmpIds.push(idsInOrder[k]);
        }
        tmpIds.push(idsInOrder[j]);
        for (k = l; k != i; k = (k + 1) % 52) {
          tmpIds.push(idsInOrder[k]);
        }
        for (k = (j + 1) % 52; k != l; k = (k + 1) % 52) {
          tmpIds.push(idsInOrder[k]);
        }
        var dist = getDist(tmpIds);
        if (dist < bestScore) {
          console.log("best score" + dist + " " + tmpIds.length);
          bestScore = dist;
          bestIdsInOrder = tmpIds.slice(0);
          done = true;
        }
      }
    }
  }
  if (origDist <= bestScore) {
    alert("Could not improve solution");
  } else {
    currentTour = bestIdsInOrder;
    var dist = drawTour(bestIdsInOrder);
  }
}

function loadBest() {
  currentTour = bestSolution;
  drawTour(currentTour);
}

function getDist(idsInOrder) {
  var dist = 0.0;
  var lastX = x[idsInOrder[0]];
  var lastY = y[idsInOrder[0]];
  for (var k = 1; k < idsInOrder.length; k++) {
    var ndx = parseInt(idsInOrder[k], 10);
    dist =
      dist +
      Math.sqrt(Math.pow(x[ndx] - lastX, 2) + Math.pow(y[ndx] - lastY, 2));
    lastX = x[ndx];
    lastY = y[ndx];
  }
  dist =
    dist +
    Math.sqrt(
      Math.pow(x[idsInOrder[0]] - lastX, 2) +
        Math.pow(y[idsInOrder[0]] - lastY, 2)
    );
  dist = dist * MILES_PER_PIXEL;
  return dist;
}
