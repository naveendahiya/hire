(function() {
    'use strict';

    angular
        .module('hireApp')
        .controller('ActivityTypeDialogController', ActivityTypeDialogController);

    ActivityTypeDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'ActivityType'];

    function ActivityTypeDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, ActivityType) {
        var vm = this;

        vm.activityType = entity;
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
            if (vm.activityType.id !== null) {
                ActivityType.update(vm.activityType, onSaveSuccess, onSaveError);
            } else {
                ActivityType.save(vm.activityType, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('hireApp:activityTypeUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
