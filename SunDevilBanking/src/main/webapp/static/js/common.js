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

        return false;
    });

    // get balance based on the acount selected
    $('#select-account-creditdebit').change(function() {
        var id = "#" + $(this).find('option:selected').prop('id') + "bal";
        $('#current-balance-credit-debit').find('p').addClass('hide');
        if(id === '#bal') {
            $('#please-select-account').removeClass('hide');
        } else {
            $(id).removeClass('hide');
        }        
        return false;
    });

});