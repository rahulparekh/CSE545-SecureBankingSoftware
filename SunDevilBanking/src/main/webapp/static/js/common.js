$(function() {

    if ($('#virtualKeyboard').length) {
        jsKeyboard.init("virtualKeyboard");

        //first input focus
        var $firstInput = $(':input').first().focus();
        jsKeyboard.currentElement = $firstInput;
        jsKeyboard.currentElementCursorPosition = 0;
        
    }    

    // Open Add Withdraw Funds modal when .add-withdraw class is clicked
    $('.add-withdraw').click( function() {
        $('#add-withdraw').modal('show');
        return false;
    });

    // Transfer type (internal or external)
    $('.transfer-option-btn').click( function() {

        $('.transfer-option-btn').removeClass('active');

        if ($(this).hasClass('internal-transfer')) {
            $('#internal-transfer').removeClass('hidden');
            $('#external-transfer').addClass('hidden');
        } else {
            $('#external-transfer').removeClass('hidden');
            $('#internal-transfer').addClass('hidden');
        }

        $(this).addClass('active');

    });

    // get balance based on the acount selected
    $('#select-account').change(function() {
        var id = "#" + $(this).find('option:selected').prop('id') + "bal";
        var creditId = "#" + $(this).find('option:selected').prop('id') + "credit";
        $('#current-balance').find('p').addClass('hide');
        if(id === '#bal') {
            $('#please-select-account').removeClass('hide');
        } else {
            $(id).removeClass('hide');
        }
        if ($('#select-receiver-account').length) {
            $('#select-receiver-account')
                .find('option')
                .removeClass('hide')
                .parent()
                .prop('selectedIndex',0);
            $(creditId).addClass("hide");
        }
        return false;
    });

});