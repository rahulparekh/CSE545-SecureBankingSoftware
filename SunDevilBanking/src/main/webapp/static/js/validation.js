/*
* JS Validation functions for all the pages go here
*/

$(function() {

    // credit debit form
    $("#credit-debit-form").validate({
        rules: {
            number: "required",
            amount: {
                required: true,
                number: true,
                min: 0.01
            },
            type: {
                required:true
            }
        },
        errorPlacement: function(error, element) {
            if (element.attr("name") == "type") {
                error.insertAfter(".type-error");
            } else {
                error.insertAfter(element);
            }
        }
    });

});