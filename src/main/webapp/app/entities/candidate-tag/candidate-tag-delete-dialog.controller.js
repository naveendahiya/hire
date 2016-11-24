(function() {
    'use strict';

    angular
        .module('hireApp')
        .controller('CandidateTagDeleteController',CandidateTagDeleteController);

    CandidateTagDeleteController.$inject = ['$uibModalInstance', 'entity', 'CandidateTag'];

    function CandidateTagDeleteController($uibModalInstance, entity, CandidateTag) {
        var vm = this;

        vm.candidateTag = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            CandidateTag.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
