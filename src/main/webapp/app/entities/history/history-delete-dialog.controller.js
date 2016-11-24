(function() {
    'use strict';

    angular
        .module('hireApp')
        .controller('HistoryDeleteController',HistoryDeleteController);

    HistoryDeleteController.$inject = ['$uibModalInstance', 'entity', 'History'];

    function HistoryDeleteController($uibModalInstance, entity, History) {
        var vm = this;

        vm.history = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            History.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
