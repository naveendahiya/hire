(function() {
    'use strict';

    angular
        .module('hireApp')
        .controller('CandidateSourceDeleteController',CandidateSourceDeleteController);

    CandidateSourceDeleteController.$inject = ['$uibModalInstance', 'entity', 'CandidateSource'];

    function CandidateSourceDeleteController($uibModalInstance, entity, CandidateSource) {
        var vm = this;

        vm.candidateSource = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            CandidateSource.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
