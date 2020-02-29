

var page = require('webpage').create();
page.open(url, function(success){

    if(success==='success'){
        console.log('success');
        page.render(savename);
        phantom.exit();

    }else{

        console.log('error');

        phantom.exit();

    }

});




