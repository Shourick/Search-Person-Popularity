$(document).ready(function(){
    var form = $('.stat');
    form.on('submit', function(e){
        e.preventDefault();
        console.log('ok');
        var site_id = $('#id').val();
        console.log('site_id=' + site_id);
        $('#politics-table').removeClass('hidden');
    })
});

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