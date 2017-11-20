var api_host = 'http://94.130.27.143/'

function get_rank_by_person_id() {
    var form = $('.stat');
    form.on('submit', function(e){
        e.preventDefault();
        console.log('ok');
        var person_id = $('#id').val();
        var url = api_host + person_id;
        console.log('person_id=' + person_id);
            $.ajax({
                type: "POST",
                url: '/rank/',
                data: {csrfmiddlewaretoken: $('input[name=csrfmiddlewaretoken]').val(),
                        url: url,
                        },
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