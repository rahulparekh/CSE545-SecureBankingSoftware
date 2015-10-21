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
                currency: ["$", false],
                min: 0.01
            },
            type: {
                required:true
            }
        },        
        messages: {
            amount: {
                currency: "Incorrect format. Valid currency formats are 0, 0.0 and 0.00."
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