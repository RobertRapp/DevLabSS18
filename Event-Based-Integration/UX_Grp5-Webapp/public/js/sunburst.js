// Diagramm wird erstellt. Als Daten wird das übergebene JsonString verwendet.
function drawDiagram(jsonFile){
	$(".docInformation").css("opacity", 0);
	
	//Größe und Radius des D3 Diagramms
var width = 960,
    height = 700,
    radius = (Math.min(width, height) / 2) - 10,
    node

//  Tooltip. Hier werden das Elemt angelegt, dass die Information bei der onmouseover Funktion angezeigt werde
var tooltip	 = d3.select("body").append("div")
.attr("class", "tooltip")
.style("opacity", 0)

var docInformation = d3.select("body").append("div")
.attr("class", "docInformation")
.style("opacity", 0)


 svg = d3.select('#docVorschlaege')
			   .append('svg')
               .attr("width", width)
               .attr("height", height)
			  .call(responsivefy)
              .append('g')
              .attr('transform', 'translate(' + width / 2 + ',' + (height / 2) + ')');




//Schatten Effekt des Hauptdiagramms und der einzelnen Elemente
var defs = svg.append("defs");

//create filter with id #drop-shadow
//height=130% so that the shadow is not clipped
var filter = defs.append("filter")
  .attr("id", "drop-shadow")
  .attr("height", "150%");
//SourceAlpha refers to opacity of graphic that this filter will be applied to
//convolve that with a Gaussian with standard deviation 3 and store result
//in blur
filter.append("feGaussianBlur")
 .attr("in", "SourceAlpha")
 .attr("stdDeviation", 5)
 .attr("result", "blur");

//translate output of Gaussian blur to the right and downwards with 2px
//store result in offsetBlur
filter.append("feOffset")
 .attr("in", "blur")
 .attr("dx", 5)
 .attr("dy", 5)
 .attr("result", "offsetBlur");

//overlay original SourceGraphic over translated blurred opacity by using
//feMerge filter. Order of specifying inputs is important!
var feMerge = filter.append("feMerge");

feMerge.append("feMergeNode")
 .attr("in", "offsetBlur")
feMerge.append("feMergeNode")
 .attr("in", "SourceGraphic");



const x = d3.scaleLinear().range([0, 2 * Math.PI])
const y = d3.scaleLinear().range([0, radius])

//const colors = d3.scaleOrdinal(d3.schemeCategory20)
const partition = d3.partition()

const arc = d3.arc()
  .startAngle(d => Math.max(0, Math.min(2 * Math.PI, x(d.x0))))
  .endAngle(d => Math.max(0, Math.min(2 * Math.PI, x(d.x1))))
  .innerRadius(d => Math.max(0, y(d.y0)))
  .outerRadius(d => Math.max(0, y(d.y1)))



 //Der ausgeklammerte Bereich wird verwendet, wenn Testdaten aus einer lokalen JSON File verwendet werden möchte 
//d3.json(jsonFile, function(error, root){   
////d3.json("./assets/testJson3.json", function(error, data){
//  if (error) return console.error(error)

  const gSlices = svg.selectAll('g')
  .data(partition(d3.hierarchy(jsonFile)
  .sum(d => d.size))
  .descendants(), function (d) { return d.data.name})
  .enter().append('g')
  .style("filter", "url(#drop-shadow)")
  //Hier werden die einzelnen Elemente dem gesamten Diagramm angelegt.
  //Jedes äußere Element erhält die jeweilige Farbe des Dokuments, eine Hoverinformation und es wird 
  //die Funktion hintelegt, dass das Dokumente geöffnet werden kann und anschließend in der Historie abgelegt wird
  gSlices.append('path')
         .style('fill', function (d) {    
           return d.data.color
         })
         .on("mouseenter", function(d) {		
        	 if (d.depth === 2 && noHover == false){
			tooltip.transition()		
                .duration(200)		
                .style("opacity", 1);	
				
			hoverStopDraw = true;
			tooltip	.html("Bezeichnung:" +d.data.name+
					"<br>Ersteller: " +d.data.Ersteller  
					)	
                .style("left", (d3.event.pageX) + "px")		
                .style("top", (d3.event.pageY - 28) + "px")

        	  }
         })
         .on("mouseleave", function(d) {		
         	tooltip.transition()		
                 .duration(200)		
                 .style("opacity", 0);
         	hoverStopDraw = false;
         })			
        .on('click', click);

        		  
  gSlices.append('text')
         .attr('dy', '.35em')
         .text(function (d) { return d.parent ? d.data.name : '' })
         .attr('id', function (d) { return 'w' + d.data.name })
         .attr('fill', function(d){return d.data.fontcolor})

          
  svg.selectAll('path')
     .transition('update')
     //duration 750
     .duration(0).attrTween('d', function (d, i) {
       return arcTweenPath(d, i)
     })

  svg.selectAll('text')
     .transition('update')
     //duration 750
     .duration(0)
     .attrTween('transform', function (d, i) { return arcTweenText(d, i)})
     .attr('text-anchor', function (d) { 
       return d.textAngle > 180 ? 'start' : 'end' 
     })
     .attr('dx', function (d) { 
       return d.textAngle > 180 ? 27: 27
     })
     .attr('opacity', function (e) { 
      return e.x1 - e.x0 > 0.01 ? 1 : 0 
     })
     //Klammer ist notwendig, wenn lokale TestJson verwendet werden will
//})


function arcTweenText(a, i) {
  var oi = d3.interpolate({ x0: (a.x0s ? a.x0s : 0), x1: (a.x1s ? a.x1s : 0), y0: (a.y0s ? a.y0s : 0), y1: (a.y1s ? a.y1s : 0) }, a)

  function tween(t) {
    var b = oi(t)
    var ang = ((x((b.x0 + b.x1) / 2) - Math.PI / 2) / Math.PI * 180)

    b.textAngle = (ang > 90) ? 180 + ang : ang
    a.centroid = arc.centroid(b)

    return 'translate(' + arc.centroid(b) + ')rotate(' + b.textAngle + ')'
  }
  return tween
}
  
function arcTweenPath(a, i) {
  var oi = d3.interpolate({ x0: (a.x0s ? a.x0s : 0), x1: (a.x1s ? a.x1s : 0), y0: (a.y0s ? a.y0s : 0), y1: (a.y1s ? a.y1s : 0) }, a)

  function tween(t) {
    var b = oi(t)

    a.x0s = b.x0
    a.x1s = b.x1
    a.y0s = b.y0
    a.y1s = b.y1

    return arc(b)
  }
  if (i == 0 && node) { 

    var xd = d3.interpolate(x.domain(), [node.x0, node.x1])
    var yd = d3.interpolate(y.domain(), [node.y0, 1])
    var yr = d3.interpolate(y.range(), [node.y0 ? 40 : 0, radius])

    return function (t) {
      x.domain(xd(t))
      y.domain(yd(t)).range(yr(t))

      return tween(t)
    }
  } else {
    // first build
    return tween
  }
}



//Funktion für das Filtern der einzelnen Elemente. Klickt man auf einen Ring der ersten Ebene, wird nach
//der jeweiligen Kategorie gefiltert. Wird auf ein Element auf der äußersten Ebene (ein Dokument) geklickt,
//werden Informationen und Buttons eines Dokuments angezeigt
function click(d) {
	if(d.depth==0){
		FilterStopDraw = false;
	}else{
		FilterStopDraw = true;
	}
	
  node = d
  const total = d.x1 - d.x0

  svg.selectAll('path')
     .transition('click')
     //duration 750
     .duration(750)
     .attrTween('d', function (d, i) { return arcTweenPath(d, i)})
     if(d.depth == 2){
    	 noHover=true;
    	 noHistorie = true;
    	 docInformation.transition()
 		 .duration(200)		
         .style("opacity", 1)	
         docInformation .html(
				 	'<div id="docInfo"> <p id="'+d.data.docID+'"> </p>'+
				 	'<h6 id="docName">' +d.data.name +'</h6>'+
				 	'<h8 id="docErsteller"> Ersteller: ' +d.data.Ersteller +'</h8>'+
				 	"</div>" +
		            '<a class="btn btn-info" onclick="openDocument(this)" href= "'+ d.data.path + '" target="_blank">' + 
		            "Dokument öffnen" +
		            "</a>" + 
		            '<br/><button type="button" class="btn btn-info" id="addFavoriteButton"  onclick="addFavorite(this)"><img src="../img/favoriten.png">' + 
		            "</button>")
		              
         
          .style("left", 55 + "%")		
          .style("top", 23 + "%");
      }else{
    	  docInformation.transition()		
    	  	.duration(500)		
    	  	.style("opacity", 0);
    	  noHover=false;
    	  noHistorie = true;
      }

     
  svg.selectAll('text')
     .transition('click')
     .attr('opacity', 1)
     //duration 750
     .duration(750)
    
     .attrTween('transform', function (d, i) { 
       return arcTweenText(d) 
      })
     .attr('text-anchor', function (d) { 
       return d.textAngle > 180 ? 'start' : 'end'
     })
     .attr('dx', function (d) {
       if(d.data.name.length > 3) {
         return d.textAngle > 180 ? -23 : 23
       }
       return d.textAngle > 180 ? -13 : 13
     })
     .attr('opacity', function (e) {
       // hide & show text
       if (e.x0 >= d.x0 && e.x1 <= (d.x1 + 0.0000000000000001)) {
         const arcText = d3.select(this.parentNode)
         	.select('text')
         	
         arcText.attr('visibility', function(d) {
           return (d.x1 - d.x0) / total < 0.01 ? 'hidden' : 'visible' 
         })
       } else {
         return 0
       }
     })
   
 }
 
//Diese Funktion ermöglicht ein responsives Design der D3 Grafik
function responsivefy(svg) {
    // get container + svg aspect ratio
    var container = d3.select(svg.node().parentNode), 
		//var container = d3.select('#docVorschlaege'), 
		width = parseInt(svg.style("width")),
        height = parseInt(svg.style("height")),
        aspect = width / height;
    // add viewBox and preserveAspectRatio properties,
    // and call resize so that svg resizes on inital page load
    svg.attr("viewBox", "0 0 " + width + " " + height)
        .attr("preserveAspectRatio", "xMinYMid")
        .call(resize);
    // to register multiple listeners for same event type,
    // you need to add namespace, i.e., 'click.foo'
    // necessary if you call invoke this function for multiple svgs
    // api docs: https://github.com/mbostock/d3/wiki/Selections#on
    d3.select(window).on("resize." + container.attr("id"), resize);
    // get width of container and resize svg to fit it
    function resize() {
    	$(".docInformation").css("opacity", 1);
        var targetWidth = parseInt(container.style("width"));
        svg.attr("width", targetWidth);
        svg.attr("height", Math.round(targetWidth / aspect));
    }
  }

}
//Funktion zum ablegen eines Dokumentes in der Favoritenanzeige
function addFavorite(e){

	//die Attribute werden aus der aktuellen Dokumentenvorschau geladen und zum anlegen des Elementes in der 
	//Favoritenansicht verwendet
	var docName = e.parentNode.firstElementChild.childNodes[2].innerHTML;
	var ersteller = e.parentNode.firstElementChild.childNodes[3].innerHTML
	var id = e.parentNode.firstElementChild.childNodes["0"].nextSibling.id;
	var pfad = e.attributes[3].ownerDocument.activeElement.parentElement.firstChild.nextElementSibling.href;
	var neuesDocElement =  
	'<li id="'+id+'"class="list-group-item"><h6>'+docName   +'</h6>&nbsp;<a class="btn btn-info"  href= "'+ pfad + '" target="_blank">' + 
    "Dokument öffnen" +
    '</a>'+
    '<button id="deleteButton" type="button" class="btn btn-danger" onclick="removeFavorite(this)">X</button></li>';
	$( "#merkerListeList" ).prepend( neuesDocElement );
	
}
//Funktion die beim Klick des Öffnen Buttons ausgefüht wird.
//die Attribute werden aus der aktuellen Dokumentenvorschau geladen und zum anlegen des Elementes in der 
//Hisrotie verwendet
function openDocument(e){
	
		var docName = e.parentNode.firstElementChild.childNodes[2].innerHTML;
		var ersteller = e.parentNode.firstElementChild.childNodes[3].innerHTML
		var id = e.parentNode.firstElementChild.childNodes["0"].nextSibling.id;
		var pfad = e.attributes[3].ownerDocument.activeElement.parentElement.firstChild.nextElementSibling.href;
		var neuesHistorieElement =  
		'<li id="'+id+'"class="list-group-item"><h6>'+docName   +'</h6>&nbsp;<a class="btn btn-info"  href= "'+ pfad + '" target="_blank">' + 
	    "Dokument öffnen" +
	    '</a>'
		$( "#historieList" ).prepend( neuesHistorieElement );
		
		//ClickedOnDocument Event an Websocket
		webSocket.send(JSON.stringify({
            type: "clickedOnDocument",
            docID: id,
            userID: userEmail,
            docName: docName
        }));
		
	}
//Blendet die Dokumenteninformationen aus damit diese nicht  während der Historienansicht eingeblendet sind.
function showHistorie(){
	if (noHistorie == true){
		$(".docInformation").css("opacity", 0);
	}
	$(".docInformation").css("opacity", 0);
}
//Zeigt die Dokumenteninformationen, die während der Historienansicht ausgblendet werden wieder ein
function hideHistorie(){
	$(".docInformation").css("opacity", 0);
}

function removeFavorite(e){
	e.parentNode.remove();
}