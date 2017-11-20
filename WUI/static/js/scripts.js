$(document).ready(function(){

    $('#persons').on('click', function(e){
        e.preventDefault();
        console.log('ok');
        $('.persons').toggleClass('hidden');
    });


    $('#sites').on('click', function(e){
        e.preventDefault();
        console.log('ok');
        $('.sites').toggleClass('hidden');
    })


});



//$(document).ready(function(){
//    var form = $('.stat');
//    form.on('submit', function(e){
//        e.preventDefault();
//        console.log('ok');
//        var site_id = $('#id').val();
//        console.log('site_id=' + site_id);
//    })
//});
//
////
//$(document).ready(function(){
//    var form = $('#"daily_stat');
//    form.on('submit', function(e){
//        e.preventDefault();
//        var submit_btn = $('#submit_btn');
//        var date_from = $('#date_from').val();
//        var date_to = $('#date_to').val();

//    })
//});