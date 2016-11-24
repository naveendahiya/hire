(function() {
    'use strict';

    angular
        .module('hireApp')
        .controller('CandidateSourceDialogController', CandidateSourceDialogController);

    CandidateSourceDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'CandidateSource'];

    function CandidateSourceDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, CandidateSource) {
        var vm = this;

        vm.candidateSource = entity;
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
            if (vm.candidateSource.id !== null) {
                CandidateSource.update(vm.candidateSource, onSaveSuccess, onSaveError);
            } else {
                CandidateSource.save(vm.candidateSource, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('hireApp:candidateSourceUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.dateCreated = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
