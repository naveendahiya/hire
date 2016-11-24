(function() {
    'use strict';

    angular
        .module('hireApp')
        .controller('CareerPortalQuestionnaireHistoryDeleteController',CareerPortalQuestionnaireHistoryDeleteController);

    CareerPortalQuestionnaireHistoryDeleteController.$inject = ['$uibModalInstance', 'entity', 'CareerPortalQuestionnaireHistory'];

    function CareerPortalQuestionnaireHistoryDeleteController($uibModalInstance, entity, CareerPortalQuestionnaireHistory) {
        var vm = this;

        vm.careerPortalQuestionnaireHistory = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            CareerPortalQuestionnaireHistory.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
