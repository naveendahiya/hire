(function() {
    'use strict';

    angular
        .module('hireApp')
        .controller('EmailHistoryDeleteController',EmailHistoryDeleteController);

    EmailHistoryDeleteController.$inject = ['$uibModalInstance', 'entity', 'EmailHistory'];

    function EmailHistoryDeleteController($uibModalInstance, entity, EmailHistory) {
        var vm = this;

        vm.emailHistory = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            EmailHistory.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
