(function() {
    'use strict';

    angular
        .module('hireApp')
        .controller('CandidateJoborderStatusDialogController', CandidateJoborderStatusDialogController);

    CandidateJoborderStatusDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'CandidateJoborderStatus'];

    function CandidateJoborderStatusDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, CandidateJoborderStatus) {
        var vm = this;

        vm.candidateJoborderStatus = entity;
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
            if (vm.candidateJoborderStatus.id !== null) {
                CandidateJoborderStatus.update(vm.candidateJoborderStatus, onSaveSuccess, onSaveError);
            } else {
                CandidateJoborderStatus.save(vm.candidateJoborderStatus, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('hireApp:candidateJoborderStatusUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
