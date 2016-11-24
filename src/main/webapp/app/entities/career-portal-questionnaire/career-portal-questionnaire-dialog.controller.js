(function() {
    'use strict';

    angular
        .module('hireApp')
        .controller('CareerPortalQuestionnaireDialogController', CareerPortalQuestionnaireDialogController);

    CareerPortalQuestionnaireDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'CareerPortalQuestionnaire'];

    function CareerPortalQuestionnaireDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, CareerPortalQuestionnaire) {
        var vm = this;

        vm.careerPortalQuestionnaire = entity;
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
            if (vm.careerPortalQuestionnaire.id !== null) {
                CareerPortalQuestionnaire.update(vm.careerPortalQuestionnaire, onSaveSuccess, onSaveError);
            } else {
                CareerPortalQuestionnaire.save(vm.careerPortalQuestionnaire, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('hireApp:careerPortalQuestionnaireUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
