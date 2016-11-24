(function() {
    'use strict';

    angular
        .module('hireApp')
        .controller('ModuleSchemaDialogController', ModuleSchemaDialogController);

    ModuleSchemaDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'ModuleSchema'];

    function ModuleSchemaDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, ModuleSchema) {
        var vm = this;

        vm.moduleSchema = entity;
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
            if (vm.moduleSchema.id !== null) {
                ModuleSchema.update(vm.moduleSchema, onSaveSuccess, onSaveError);
            } else {
                ModuleSchema.save(vm.moduleSchema, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('hireApp:moduleSchemaUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
