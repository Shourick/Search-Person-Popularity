$(document).ready(function(){
    var form = $('#general_stat');
    form.on('submit', function(e){
        e.preventDefault();
        console.log('ok');
//        var submit_btn = $('#submit_btn');
        var site_id = $('#id').val();
        console.log('site_id=' + site_id);
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