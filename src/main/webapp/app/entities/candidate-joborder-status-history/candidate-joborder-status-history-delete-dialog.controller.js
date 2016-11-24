(function() {
    'use strict';

    angular
        .module('hireApp')
        .controller('CandidateJoborderStatusHistoryDeleteController',CandidateJoborderStatusHistoryDeleteController);

    CandidateJoborderStatusHistoryDeleteController.$inject = ['$uibModalInstance', 'entity', 'CandidateJoborderStatusHistory'];

    function CandidateJoborderStatusHistoryDeleteController($uibModalInstance, entity, CandidateJoborderStatusHistory) {
        var vm = this;

        vm.candidateJoborderStatusHistory = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            CandidateJoborderStatusHistory.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
