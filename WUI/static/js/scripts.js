$(document).ready(function(){

    $('#persons').on('click', function(e){
        e.preventDefault();
        $('.persons').toggleClass('hidden');
    });


    $('#sites').on('click', function(e){
        e.preventDefault();
        $('.sites').toggleClass('hidden');
    })


});

$(function(){
 $('.date').daterangepicker();
});

$(function(){
 $('.date').daterangepicker({
  singleDatePicker: true,
  locale: {
 format: 'YYYY-MM-DD'
  }
 });
});