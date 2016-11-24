(function() {
    'use strict';

    angular
        .module('hireApp')
        .controller('CandidateJobordrerStatusTypeDialogController', CandidateJobordrerStatusTypeDialogController);

    CandidateJobordrerStatusTypeDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'CandidateJobordrerStatusType'];

    function CandidateJobordrerStatusTypeDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, CandidateJobordrerStatusType) {
        var vm = this;

        vm.candidateJobordrerStatusType = entity;
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
            if (vm.candidateJobordrerStatusType.id !== null) {
                CandidateJobordrerStatusType.update(vm.candidateJobordrerStatusType, onSaveSuccess, onSaveError);
            } else {
                CandidateJobordrerStatusType.save(vm.candidateJobordrerStatusType, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('hireApp:candidateJobordrerStatusTypeUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
