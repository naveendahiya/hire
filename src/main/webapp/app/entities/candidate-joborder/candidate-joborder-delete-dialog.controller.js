(function() {
    'use strict';

    angular
        .module('hireApp')
        .controller('CandidateJoborderDeleteController',CandidateJoborderDeleteController);

    CandidateJoborderDeleteController.$inject = ['$uibModalInstance', 'entity', 'CandidateJoborder'];

    function CandidateJoborderDeleteController($uibModalInstance, entity, CandidateJoborder) {
        var vm = this;

        vm.candidateJoborder = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            CandidateJoborder.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
