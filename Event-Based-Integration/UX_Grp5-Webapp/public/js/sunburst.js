var width = 960,
    height = 700,
    radius = (Math.min(width, height) / 2) - 10,
    node
    
//  Tooltip description
//var div = d3.select("body").append("div")
//.attr("class", "tooltip")
//.style("opacity", 0);
//    
var svg = d3.select('#docVorschlaege')
			   .append('svg')
               .attr("width", width)
               .attr("height", height)
               // .attr("preserveAspectRatio", "xMinYMin meet")
//			.attr("viewBox", "0 0 " + width + " " + height)
//			 .attr("preserveAspectRatio", "xMinYMid")
			 .call(responsivefy)
              .append('g')
              .attr('transform', 'translate(' + width / 2 + ',' + (height / 2) + ')');
				
             // d3.select(window).on("resize." + container.attr("id"), resize);



const x = d3.scaleLinear().range([0, 2 * Math.PI])
const y = d3.scaleLinear().range([0, radius])

//const colors = d3.scaleOrdinal(d3.schemeCategory20)
const partition = d3.partition()

const arc = d3.arc()
  .startAngle(d => Math.max(0, Math.min(2 * Math.PI, x(d.x0))))
  .endAngle(d => Math.max(0, Math.min(2 * Math.PI, x(d.x1))))
  .innerRadius(d => Math.max(0, y(d.y0)))
  .outerRadius(d => Math.max(0, y(d.y1)))



  
  
d3.json("./assets/testJson.json", function(error, root){
  if (error) return console.error(error)

  const gSlices = svg.selectAll('g')
  .data(partition(d3.hierarchy(root)
  .sum(d => d.size))
  .descendants(), function (d) { return d.data.name})
  .enter().append('g')
  
  
  gSlices.exit().remove()
  
  gSlices.append('path')
         .style('fill', function (d) { 
//           if (d.depth === 0) return 'white'
           return d.data.color
         })
		.on("mouseover", function(d) {		
            div.transition()		
                .duration(200)		
                .style("opacity", .9);		
            div	.html(d.data.Ersteller)	
                .style("left", (d3.event.pageX) + "px")		
                .style("top", (d3.event.pageY - 28) + "px");
            })					
        .on("mouseout", function(d) {		
            div.transition()		
                .duration(500)		
                .style("opacity", 0);
        })
        .on('click', click);
  
  gSlices.append('text')
         .attr('dy', '.35em')
         .text(function (d) { return d.parent ? d.data.name : '' })
         .attr('id', function (d) { return 'w' + d.data.name })
         .attr('fill', '#fff')

  svg.selectAll('path')
     .transition('update')
     .duration(750).attrTween('d', function (d, i) {
       return arcTweenPath(d, i)
     })

  svg.selectAll('text')
     .transition('update')
     .duration(750)
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

     
     
     
})

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




function click(d) {
  node = d
  const total = d.x1 - d.x0

  svg.selectAll('path')
     .transition('click')
     .duration(750)
     .attrTween('d', function (d, i) { return arcTweenPath(d, i)})

  svg.selectAll('text')
     .transition('click')
     .attr('opacity', 1)
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
         const arcText = d3.select(this.parentNode).select('text')
         arcText.attr('visibility', function(d) {
           return (d.x1 - d.x0) / total < 0.01 ? 'hidden' : 'visible' 
         })
       } else {
         return 0
       }
     })
 }
 
 

function mouseOverArc(d) {
	 d3.select(this).attr("stroke","black")
	 
// tooltip.html(format_description(d));
	 tooltip.html("Ersteller: " + d.data.Ersteller)
 return tooltip.transition()
   .duration(50)
   .style("opacity", 0.9);
}

function mouseOutArc(){
d3.select(this).attr("stroke","")
return tooltip.style("opacity", 0);
}

function mouseMoveArc (d) {
 return tooltip
   .style("top", (d3.event.pageY-10)+"px")
   .style("left", (d3.event.pageX+10)+"px");
}

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
        var targetWidth = parseInt(container.style("width"));
        svg.attr("width", targetWidth);
        svg.attr("height", Math.round(targetWidth / aspect));
    }
  }