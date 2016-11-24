(function() {
    'use strict';

    angular
        .module('hireApp')
        .controller('AccessLevelDialogController', AccessLevelDialogController);

    AccessLevelDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'AccessLevel'];

    function AccessLevelDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, AccessLevel) {
        var vm = this;

        vm.accessLevel = entity;
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
            if (vm.accessLevel.id !== null) {
                AccessLevel.update(vm.accessLevel, onSaveSuccess, onSaveError);
            } else {
                AccessLevel.save(vm.accessLevel, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('hireApp:accessLevelUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
