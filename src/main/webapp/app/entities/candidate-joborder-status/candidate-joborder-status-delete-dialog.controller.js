(function() {
    'use strict';

    angular
        .module('hireApp')
        .controller('CandidateJoborderStatusDeleteController',CandidateJoborderStatusDeleteController);

    CandidateJoborderStatusDeleteController.$inject = ['$uibModalInstance', 'entity', 'CandidateJoborderStatus'];

    function CandidateJoborderStatusDeleteController($uibModalInstance, entity, CandidateJoborderStatus) {
        var vm = this;

        vm.candidateJoborderStatus = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            CandidateJoborderStatus.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
