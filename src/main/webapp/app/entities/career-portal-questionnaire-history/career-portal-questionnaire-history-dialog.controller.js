(function() {
    'use strict';

    angular
        .module('hireApp')
        .controller('CareerPortalQuestionnaireHistoryDialogController', CareerPortalQuestionnaireHistoryDialogController);

    CareerPortalQuestionnaireHistoryDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'CareerPortalQuestionnaireHistory'];

    function CareerPortalQuestionnaireHistoryDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, CareerPortalQuestionnaireHistory) {
        var vm = this;

        vm.careerPortalQuestionnaireHistory = entity;
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
            if (vm.careerPortalQuestionnaireHistory.id !== null) {
                CareerPortalQuestionnaireHistory.update(vm.careerPortalQuestionnaireHistory, onSaveSuccess, onSaveError);
            } else {
                CareerPortalQuestionnaireHistory.save(vm.careerPortalQuestionnaireHistory, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('hireApp:careerPortalQuestionnaireHistoryUpdate', result);
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
