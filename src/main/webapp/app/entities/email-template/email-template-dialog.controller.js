(function() {
    'use strict';

    angular
        .module('hireApp')
        .controller('EmailTemplateDialogController', EmailTemplateDialogController);

    EmailTemplateDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'EmailTemplate'];

    function EmailTemplateDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, EmailTemplate) {
        var vm = this;

        vm.emailTemplate = entity;
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
            if (vm.emailTemplate.id !== null) {
                EmailTemplate.update(vm.emailTemplate, onSaveSuccess, onSaveError);
            } else {
                EmailTemplate.save(vm.emailTemplate, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('hireApp:emailTemplateUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
