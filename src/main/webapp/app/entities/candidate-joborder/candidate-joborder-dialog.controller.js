(function() {
    'use strict';

    angular
        .module('hireApp')
        .controller('CandidateJoborderDialogController', CandidateJoborderDialogController);

    CandidateJoborderDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'CandidateJoborder'];

    function CandidateJoborderDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, CandidateJoborder) {
        var vm = this;

        vm.candidateJoborder = entity;
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
            if (vm.candidateJoborder.id !== null) {
                CandidateJoborder.update(vm.candidateJoborder, onSaveSuccess, onSaveError);
            } else {
                CandidateJoborder.save(vm.candidateJoborder, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('hireApp:candidateJoborderUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.dateSubmitted = false;
        vm.datePickerOpenStatus.dateCreated = false;
        vm.datePickerOpenStatus.dateModified = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
