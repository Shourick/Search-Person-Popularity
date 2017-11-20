//var api_host = 'http://94.130.27.143/1/'


function get_site_id() {
    var form = $('.stat');
    form.on('submit', function(e){
        e.preventDefault();
        console.log('ok');
        var site_id = $('#id').val();
//        var url = api_host + site_id;
        console.log('site_id=' + site_id);
            $.ajax({
                type: "GET",
                url: '/rank/ + String(site_id),
                success: function(){
                    console.log('func ok');
                    },
                error: function (xhr, status, error) {
                    console.log('error =', error);
                }
            });

    });
};



//
//$(document).ready(function(){
//    var form = $('#"daily_stat');
//    form.on('submit', function(e){
//        e.preventDefault();
//        var submit_btn = $('#submit_btn');
//        var date_from = $('#date_from').val();
//        var date_to = $('#date_to').val();

//    })
//});