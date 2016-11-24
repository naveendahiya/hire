(function() {
    'use strict';

    angular
        .module('hireApp')
        .controller('ActivityTypeDeleteController',ActivityTypeDeleteController);

    ActivityTypeDeleteController.$inject = ['$uibModalInstance', 'entity', 'ActivityType'];

    function ActivityTypeDeleteController($uibModalInstance, entity, ActivityType) {
        var vm = this;

        vm.activityType = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            ActivityType.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
