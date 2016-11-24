(function() {
    'use strict';

    angular
        .module('hireApp')
        .controller('CandidateJobordrerStatusTypeDeleteController',CandidateJobordrerStatusTypeDeleteController);

    CandidateJobordrerStatusTypeDeleteController.$inject = ['$uibModalInstance', 'entity', 'CandidateJobordrerStatusType'];

    function CandidateJobordrerStatusTypeDeleteController($uibModalInstance, entity, CandidateJobordrerStatusType) {
        var vm = this;

        vm.candidateJobordrerStatusType = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            CandidateJobordrerStatusType.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
