(function() {
    'use strict';

    angular
        .module('hireApp')
        .controller('CandidateTagDialogController', CandidateTagDialogController);

    CandidateTagDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'CandidateTag'];

    function CandidateTagDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, CandidateTag) {
        var vm = this;

        vm.candidateTag = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.candidateTag.id !== null) {
                CandidateTag.update(vm.candidateTag, onSaveSuccess, onSaveError);
            } else {
                CandidateTag.save(vm.candidateTag, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('hireApp:candidateTagUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
