(function() {
    'use strict';

    angular
        .module('hireApp')
        .controller('CareerPortalTemplateDialogController', CareerPortalTemplateDialogController);

    CareerPortalTemplateDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'CareerPortalTemplate'];

    function CareerPortalTemplateDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, CareerPortalTemplate) {
        var vm = this;

        vm.careerPortalTemplate = entity;
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
            if (vm.careerPortalTemplate.id !== null) {
                CareerPortalTemplate.update(vm.careerPortalTemplate, onSaveSuccess, onSaveError);
            } else {
                CareerPortalTemplate.save(vm.careerPortalTemplate, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('hireApp:careerPortalTemplateUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
