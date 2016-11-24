(function() {
    'use strict';

    angular
        .module('hireApp')
        .controller('CareerPortalQuestionnaireAnswerDialogController', CareerPortalQuestionnaireAnswerDialogController);

    CareerPortalQuestionnaireAnswerDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'CareerPortalQuestionnaireAnswer'];

    function CareerPortalQuestionnaireAnswerDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, CareerPortalQuestionnaireAnswer) {
        var vm = this;

        vm.careerPortalQuestionnaireAnswer = entity;
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
            if (vm.careerPortalQuestionnaireAnswer.id !== null) {
                CareerPortalQuestionnaireAnswer.update(vm.careerPortalQuestionnaireAnswer, onSaveSuccess, onSaveError);
            } else {
                CareerPortalQuestionnaireAnswer.save(vm.careerPortalQuestionnaireAnswer, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('hireApp:careerPortalQuestionnaireAnswerUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
