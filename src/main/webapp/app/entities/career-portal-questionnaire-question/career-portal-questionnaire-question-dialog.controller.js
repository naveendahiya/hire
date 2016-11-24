(function() {
    'use strict';

    angular
        .module('hireApp')
        .controller('CareerPortalQuestionnaireQuestionDialogController', CareerPortalQuestionnaireQuestionDialogController);

    CareerPortalQuestionnaireQuestionDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'CareerPortalQuestionnaireQuestion'];

    function CareerPortalQuestionnaireQuestionDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, CareerPortalQuestionnaireQuestion) {
        var vm = this;

        vm.careerPortalQuestionnaireQuestion = entity;
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
            if (vm.careerPortalQuestionnaireQuestion.id !== null) {
                CareerPortalQuestionnaireQuestion.update(vm.careerPortalQuestionnaireQuestion, onSaveSuccess, onSaveError);
            } else {
                CareerPortalQuestionnaireQuestion.save(vm.careerPortalQuestionnaireQuestion, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('hireApp:careerPortalQuestionnaireQuestionUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
