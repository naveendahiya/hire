(function() {
    'use strict';

    angular
        .module('hireApp')
        .controller('CandidateJoborderStatusHistoryDialogController', CandidateJoborderStatusHistoryDialogController);

    CandidateJoborderStatusHistoryDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'CandidateJoborderStatusHistory'];

    function CandidateJoborderStatusHistoryDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, CandidateJoborderStatusHistory) {
        var vm = this;

        vm.candidateJoborderStatusHistory = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.candidateJoborderStatusHistory.id !== null) {
                CandidateJoborderStatusHistory.update(vm.candidateJoborderStatusHistory, onSaveSuccess, onSaveError);
            } else {
                CandidateJoborderStatusHistory.save(vm.candidateJoborderStatusHistory, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('hireApp:candidateJoborderStatusHistoryUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.date = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
