(function() {
    'use strict';

    angular
        .module('hireApp')
        .controller('AttachmentController', AttachmentController);

    AttachmentController.$inject = ['$scope', '$state', 'Attachment'];

    function AttachmentController ($scope, $state, Attachment) {
        var vm = this;

        vm.attachments = [];

        loadAll();

        function loadAll() {
            Attachment.query(function(result) {
                vm.attachments = result;
            });
        }
    }
})();
