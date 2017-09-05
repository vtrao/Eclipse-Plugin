var itxturl='http://geekstogo.us.intellitxt.com/v3/door.jsp?ts='+(new Date()).getTime()+'&ipid=1605&mk=3&refurl='+document.location.href.replace(/\&/g,'%26');
try {
document.write('<s'+'cript language="javascript" src="'+itxturl+'"></s'+'cript>');
}catch(e){}
