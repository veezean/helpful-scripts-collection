var page = require('webpage').create(),
    system = require('system'),
    address, output, size;

if (system.args.length < 3 || system.args.length > 5) {
    console.log('Usage: rasterize.js URL filename');
    phantom.exit(1);
} else {
    address = system.args[1];
    output = system.args[2];

    console.log(page.settings.userAgent );

    // 不加分页的话，只输出一页pdf，底部会有大量空白。
    //google "phantomjs render pdf bottom blank" 可以看到别人也报过这种bug
    // 最终查找Phantomjs generates multiple page PDF 竟然解决了问题
    page.paperSize = { format: 'A4', orientation: 'portrait', margin: '0.8cm' };
    // page.viewportSize = { width: 600, height: 800 };
    console.log("open page now");
    page.open(address, function (status) {


        console.log(status);

        //evaluate注册一个回调函数，页面加载完就执行。用来操作页面中的DOM元素

        //修改微信公众号文章中图片lazy load的方式
        var size = page.evaluate(function () {
            //此函数在目标页面执行的，上下文环境非本phantomjs，所以不能用到这个js中其他变量

            //这个控制台打印的效果在phantomjs执行界面看不到
            console.log('start image lazy loading');
            var imgs = document.getElementsByTagName('img');
            var size = 0;

            console.log(size);

            for (var i = imgs.length - 1; i >= 0; i--) {
              var data_src = imgs[i].getAttribute("data-src");
              if (data_src){
                imgs[i].setAttribute("src", data_src);
                size++;
              }
            }
            return size;

        } );
        console.log("change lazy load img number:" + size);

        // 预留一定的渲染时间
        window.setTimeout(function () {
            page.render(output);
            page.close();
            console.log('render ok');
            phantom.exit();
        }, 5000);
    });
};
// phantom.exit();



